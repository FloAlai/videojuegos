package org.factoriaf5.videojuegos.controllers;

import org.factoriaf5.videojuegos.models.Game;
import org.factoriaf5.videojuegos.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    String addGame(@ModelAttribute Game game, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        game.setPhoto(fileName);
        gameService.save(game);
        String uploadDir = "game-photo/" + game.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
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
