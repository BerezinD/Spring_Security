package com.frankmoley.security.app;

import com.frankmoley.security.app.domain.Guest;
import com.frankmoley.security.app.domain.GuestModel;
import com.frankmoley.security.app.service.GuestService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Frank P. Moley III.
 */
@Controller
@RequestMapping("/")
public class GuestController {

    private final GuestService guestService;
    private final String GUEST_VIEW_PAGE = "guest-view";
    private final String GUEST_PAGE = "guest";

    public GuestController(GuestService guestService){
        super();
        this.guestService = guestService;
    }

    @GetMapping(value={"/", "/index"})
    public String getHomePage(Model model){

        return "index";
    }

    @GetMapping(value="/guests")
    public String getGuests(Model model){
        List<Guest> guests = this.guestService.getAllGuests();
        model.addAttribute("guests", guests);
        return "guests-view";
    }

    @GetMapping(value="/guests/add")
    public String getAddGuestForm(Model model){
        return GUEST_VIEW_PAGE;
    }

    @PostMapping(value="/guests")
    public ModelAndView addGuest(HttpServletRequest request, Model model, @ModelAttribute GuestModel guestModel){
        Guest guest = this.guestService.addGuest(guestModel);
        model.addAttribute(GUEST_PAGE, guest);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        return new ModelAndView("redirect:/guests/" + guest.getId());
    }

    @GetMapping(value="/guests/{id}")
    public String getGuest(Model model, @PathVariable long id){
        Guest guest = this.guestService.getGuest(id);
        model.addAttribute(GUEST_PAGE, guest);
        return GUEST_VIEW_PAGE;
    }

    @PostMapping(value="/guests/{id}")
    public String updateGuest(Model model, @PathVariable long id, @ModelAttribute GuestModel guestModel){
        Guest guest = this.guestService.updateGuest(id, guestModel);
        model.addAttribute(GUEST_PAGE, guest);
        model.addAttribute("guestModel", new GuestModel());
        return GUEST_VIEW_PAGE;
    }
}
