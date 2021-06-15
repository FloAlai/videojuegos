package org.factoriaf5.videojuegos.controllers;

import org.factoriaf5.videojuegos.models.Game;
import org.factoriaf5.videojuegos.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/games")
    String listGames(Model model) {
        List<Game> games = gameService.allGames();
        model.addAttribute("title", "Games list");
        model.addAttribute("games", games);
        return "games/all";
    }
    @GetMapping("/games/new")
    String newGame(Model model) {
        Game game = new Game();
        model.addAttribute("game", game);
        model.addAttribute("title", "Create new game");
        return "games/edit";

    }
    @PostMapping("/games/new")
    String addGame(@ModelAttribute Game game) {
        gameService.save(game);
        return "redirect:/games";
    }
  @GetMapping("games/edit/{id}")
    String editGame(Model model, @PathVariable Long id){
        Game game = gameService.findById(id);
       model.addAttribute("game", game);
        model.addAttribute("title", "Edit game");
      return "games/edit";
    }

    @GetMapping("games/delete/{id}")
    String removeGame(@PathVariable Long id){
        gameService.delete(id);
        return "redirect:/games";
   }


}
