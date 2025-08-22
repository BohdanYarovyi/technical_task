package technikal.task.fishmarket.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import technikal.task.fishmarket.models.Fish;
import technikal.task.fishmarket.models.ProductImage;
import technikal.task.fishmarket.models.dto.FishDto;
import technikal.task.fishmarket.services.FishRepository;
import technikal.task.fishmarket.services.handler.ImageHandler;
import technikal.task.fishmarket.util.ProductImageValidator;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/fish")
public class FishController {

	@Autowired
	private FishRepository repo;
	@Autowired
	private ImageHandler imageHandler;

	@GetMapping({"", "/"})
	public String showFishList(Model model) {
		List<Fish> fishlist = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		model.addAttribute("fishlist", fishlist);
		System.out.println(fishlist);
		return "index";
	}

	@GetMapping("/create")
	public String showCreatePage(Model model) {
		FishDto fishDto = new FishDto();
		model.addAttribute("fishDto", fishDto);
		return "createFish";
	}

	@GetMapping("/delete")
	public String deleteFish(@RequestParam int id) {
		try {
			Fish fish = repo.findById(id).get();
			imageHandler.deleteProductImages(fish.getProductImages());
			repo.delete(fish);
		}catch(Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}

		return "redirect:/fish";
	}

	@PostMapping("/create")
	public String addFish(@Valid @ModelAttribute FishDto fishDto, BindingResult result) {
		List<MultipartFile> images = fishDto.getImageFiles();

		// validate list of images
		if (ProductImageValidator.hasListOnlyFakeFiles(images)) {
			result.addError(new FieldError("fishlist", "imageFiles", "Треба додати хоча б одне зображення"));
		}

		if(result.hasErrors()) {
			return "createFish";
		}

		Date catchDate = new Date();
		List<ProductImage> productImages = imageHandler.handleProductImages(images);

		Fish fish = new Fish();
		fish.setCatchDate(catchDate);
		fish.setProductImages(productImages);
		fish.setName(fishDto.getName());
		fish.setPrice(fishDto.getPrice());
		productImages.forEach(image -> image.setFish(fish));

		repo.save(fish);

		return "redirect:/fish";
	}

}
