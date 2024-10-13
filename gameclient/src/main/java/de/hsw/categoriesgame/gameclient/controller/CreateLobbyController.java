package de.hsw.categoriesgame.gameclient.controller;

import de.hsw.categoriesgame.gameapi.api.CategorieGame;
import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.api.Lobby;
import de.hsw.categoriesgame.gameapi.pojo.GameConfigs;
import de.hsw.categoriesgame.gameapi.util.RandomStringUtil;
import de.hsw.categoriesgame.gameclient.ClientImpl;
import de.hsw.categoriesgame.gameclient.models.GameModel;
import de.hsw.categoriesgame.gameclient.views.CreateLobbyView;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Controller class for CreateLobbyView to make operations possible
 */
public class CreateLobbyController {

    private static final Logger log = LoggerFactory.getLogger(CreateLobbyController.class);
    private final ViewManager viewManager;
    private final CreateLobbyView view;
    private final GameModel gameModel;

    /**
     * Constructor
     * @param viewManager   View Manager in order to navigate between different views
     * @param view          referring view of the controller
     * @param gameModel     model to save specific data
     */
    public CreateLobbyController(final ViewManager viewManager,
                                 final CreateLobbyView view,
                                 final GameModel gameModel)
    {
        this.viewManager = viewManager;
        this.view = view;
        this.gameModel = gameModel;

        gameModel.reset();
        registerListener();
        loadLobbyCode();

        addNewCategory("Stadt");
        addNewCategory("Land");
        addNewCategory("Fluss");
    }



    /**
     * registers all ActionListeners
     */
    private void registerListener()
    {
        view.getCancelButton().addActionListener(e -> goToStartView());
        view.getCreateButton().addActionListener(e -> goToGameRoundView());
        view.getAddCategoryButton().addActionListener(e -> addNewCategory(view.getNewCategoryInput().getText()));
        view.getNewCategoryInput().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addNewCategory(view.getNewCategoryInput().getText());
                }
            }
        });
        view.getCategoryButton().addActionListener(e -> removeCategory(view.getCategoryButton()));
        view.getReloadLobbyCodeButton().addActionListener(e -> loadLobbyCode());

        view.getAdminUsernameInput().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    goToGameRoundView();
                }
            }
        });
    }

    private void loadLobbyCode()
    {
        view.getLobbyCodeInput().setText(RandomStringUtil.generate(RandomStringUtil.Type.CAPITAL_DIGITS, 4));
    }


    /**
     * Navigates to the start screen
     */
    private void goToStartView()
    {
        log.info("GO TO START VIEW");
        viewManager.changeView(View.START);
    }


    /**
     * Navigates into the game round view including saving the amount of rounds in the model
     */
    private void goToGameRoundView()
    {
        // Validate Admin username
        final String adminUsername = view.getAdminUsernameInput().getText();
        if (adminUsername.isBlank()) {
            view.throwErrorDialog("Der Benutzername kann nicht leer sein!");
            return;
        }
        final Client admin = new ClientImpl(gameModel, view.getAdminUsernameInput().getText());


        // Validate config
        if (!validateInputs()) {
            log.error("Unable to create a new game lobby due to invalid game configuration");
            view.throwErrorDialog("Fehler in der Spielkonfiguration!\n" +
                    "- Sind Kategorien hinzugef\u00FCgt?");
            return;
        }

  
        // Create Game config
        final GameConfigs config = new GameConfigs(
                (Integer) view.getAmountRoundsSpinner().getValue(),
                (Integer) view.getMaxPlayerSpinner().getValue());
        config.setCategories(view.getCategoryButtons().stream().map(JButton::getText).toList());


        // Create Lobby
        final Lobby lobby;
        try {
            final CategorieGame game = viewManager.getProxyFactory().createProxy(CategorieGame.class);

            lobby = game.createLobby(view.getLobbyCodeInput().getText(), config);
            gameModel.setLobby(lobby);
            gameModel.setLocalClient(admin);

            game.joinLobby(lobby.getLobbyCode(), admin);

            gameModel.initialize();


        } catch (Exception e) {
            log.error("Unable to create lobby", e);
            view.throwErrorDialog("Es ist ein Fehler aufgetreten!\nF\u00FCr weitere Informationen sehen Sie bitte im Protokoll nach.");
            loadLobbyCode();
            return;
        }

        log.info("Created a new game lobby with code {}", lobby.getLobbyCode());
        viewManager.changeView(View.WAITING);
    }


    /**
     * Validates if all input is given correctly, so the player can continue
     * @return  true - conditions are met / false - conditions are not met
     */
    private boolean validateInputs()
    {
        int maxPlayers = (int) view.getMaxPlayerSpinner().getValue();
        JTextField lobbyCode = view.getLobbyCodeInput();
        int amountCategories = view.getCategoryButtons().size();

        return maxPlayers >= 2 && !lobbyCode.getText().isEmpty() && amountCategories >= 1;
    }


    /**
     * Method triggers the addition of a new category including adding it to the model
     */
    private void addNewCategory(String newCategory) {
        newCategory = newCategory.trim();
        JButton categoryButton = new JButton();

        // Max of 5 reached?
        if (view.getCategoryButtons().size() >= 5) {
            view.throwErrorDialog("Es k\u00F6nnen maximal f\u00FCnf Kategorien hinzugef\u00FCgt werden!");
            return;
        }

        // Already exists?
        String finalNewCategory = newCategory;
        if (view.getCategoryButtons().stream().map(j -> j.getText().trim()).anyMatch(j -> j.equals(finalNewCategory))) {
            view.throwErrorDialog("Diese Kategorie existiert bereits!");
            return;
        }

        for (int i = 0; i < view.getCategoryButtons().size(); i++) {
            if (view.getCategoryButtons().get(i).getText().equals(newCategory)) {
                categoryButton = view.getCategoryButtons().get(i);
            }
        }

        if (!newCategory.isEmpty()) {
            // Adding a new button component and getting the reference
            categoryButton = view.addActiveCategory(newCategory);

            // Adding action listener to the newly created category button
            JButton finalCategoryButton = categoryButton;
            categoryButton.addActionListener(e -> removeCategory(finalCategoryButton));

            // Clear input
            view.getNewCategoryInput().setText("");
        }
    }


    /**
     * Removes a certain category
     */
    private void removeCategory(JButton categoryButton) {
        // Removing the button component on the view
        view.removeCategory(categoryButton);
    }
}
