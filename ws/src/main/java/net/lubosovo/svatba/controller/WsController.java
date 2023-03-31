package net.lubosovo.svatba.controller;

import net.lubosovo.svatba.service.GiftService;
import net.lubosovo.svatba.service.ServiceException;
import net.lubosovo.svatba.repository.domain.Gift;
import net.lubosovo.svatba.repository.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;
import java.security.Principal;

@Controller
@RequestMapping(value = {"/"})
public class WsController {

    @Autowired
    private GiftService giftService;

    @RequestMapping(method = RequestMethod.GET, value = {"", "announcement", "announcement/introduction"})
    public ModelAndView getAnnouncementIntroduction() {
        return new ModelAndView("announcement/introduction");
    }

    @RequestMapping(method = RequestMethod.GET, value = {"announcement/message"})
    public ModelAndView getAnnouncementMessage() {
        return new ModelAndView("announcement/message");
    }

    @RequestMapping(method = RequestMethod.GET, value = {"announcement/poetry"})
    public ModelAndView getAnnouncementPoetry() {
        return new ModelAndView("announcement/poetry");
    }

    @RequestMapping(method = RequestMethod.GET, value = {"invitation", "invitation/nuptials_venue"})
    public ModelAndView getInvitationNuptialsVenue() {
        return new ModelAndView("invitation/nuptials_venue");
    }

    @RequestMapping(method = RequestMethod.GET, value = "invitation/nuptials_directions")
    public ModelAndView getInvitationNuptialsDirections() {
        return new ModelAndView("invitation/nuptials_directions");
    }

    @RequestMapping(method = RequestMethod.GET, value = {"invitation/party_venue"})
    public ModelAndView getInvitationPartyVenue() {
        return new ModelAndView("invitation/party_venue");
    }

    @RequestMapping(method = RequestMethod.GET, value = "invitation/party_directions")
    public ModelAndView getInvitationPartyDirections() {
        return new ModelAndView("invitation/party_directions");
    }

    @RequestMapping(method = RequestMethod.GET, value = "invitation/clarification")
    public ModelAndView getInvitationClarification() {
        return new ModelAndView("invitation/clarification");
    }

    @RequestMapping(method = RequestMethod.GET, value = "invitation/accommodation")
    public ModelAndView getInvitationAccommodation() {
        return new ModelAndView("invitation/accommodation");
    }

    @RequestMapping(method = RequestMethod.GET, value = {"gifts", "gifts/disclaimer"})
    public ModelAndView getGiftsDisclaimer() {
        return new ModelAndView("gifts/disclaimer");
    }

    @RequestMapping(method = RequestMethod.GET, value = {"gifts/howto"})
    public ModelAndView getGiftsHowto() {
        return new ModelAndView("gifts/howto");
    }

    @RequestMapping(method = RequestMethod.GET, value = {"gifts/list"})
    public ModelAndView getGiftsList() {
        List<Gift> gifts = this.giftService.getAllGifts();
        ModelAndView mav = new ModelAndView("gifts/list");
        mav.addObject("gifts", gifts);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, value = {"bestmanzone/login"})
    public ModelAndView getBestManZoneLogin(Principal principal) {
        ModelAndView mav = new ModelAndView("bestmanzone/login");
        mav.addObject("principal", principal);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, value = {"bestmanzone", "bestmanzone/gifts"})
    public ModelAndView getBestManZoneGifts(Principal principal) {
        List<Gift> gifts = this.giftService.getAllGifts();
        ModelAndView mav = new ModelAndView("bestmanzone/gifts");
        mav.addObject("principal", principal);
        mav.addObject("gifts", gifts);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, value = {"bestmanzone/gift/{id}"})
    public ModelAndView getBestManZoneGift(Principal principal, @PathVariable final Long id) {
        ModelAndView mav;
        try {
            Gift gift = this.giftService.getGiftById(id);
            mav = new ModelAndView("bestmanzone/gift");
            mav.addObject("gift", gift);
            List<User> users = this.giftService.getAllUsers();
            mav.addObject("users", users);
        } catch (ServiceException e) {
            mav = new ModelAndView("redirect:/bestmanzone/gifts");
        }
        mav.addObject("principal", principal);
        return mav;
    }

    @RequestMapping(method = RequestMethod.PUT, value = {"bestmanzone/gift/{id}"})
    public ModelAndView setBestManZoneGift(Principal principal, @PathVariable final Long id, @RequestParam("select_user") final Long id_user) {
        ModelAndView mav;
        try {
            this.giftService.setGiftById(id, id_user);
            mav = new ModelAndView("redirect:/bestmanzone/gift/" + id.toString());
        } catch (ServiceException e) {
            mav = new ModelAndView("redirect:/bestmanzone/gifts");
        }
        mav.addObject("principal", principal);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, value = {"bestmanzone/available_gifts"})
    public ModelAndView getBestManZoneGiftsAvailable(Principal principal) {
        List<Gift> gifts = this.giftService.getAvailableGifts();
        ModelAndView mav = new ModelAndView("bestmanzone/available_gifts");
        mav.addObject("principal", principal);
        mav.addObject("gifts", gifts);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, value = {"bestmanzone/users"})
    public ModelAndView getBestManZoneUsers(Principal principal) {
        List<User> users = this.giftService.getAllUsers();
        ModelAndView mav = new ModelAndView("bestmanzone/users");
        mav.addObject("principal", principal);
        mav.addObject("users", users);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, value = {"bestmanzone/new_user"})
    public ModelAndView getBestManZoneNewUser(Principal principal) {
        ModelAndView mav = new ModelAndView("bestmanzone/new_user");
        mav.addObject("principal", principal);
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, value = {"bestmanzone/new_user"})
    public ModelAndView addBestManZoneNewUser(Principal principal, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        ModelAndView mav;
        if (bindingResult.hasErrors()) {
            //fiks
            mav = new ModelAndView("redirect:/pictures");
        } else {
            try {
                this.giftService.addUser(user);
            } catch (ServiceException e) {
                //fiks
            }
            mav = new ModelAndView("redirect:/bestmanzone/users");
        }
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, value = {"bestmanzone/delete_user/{id}"})
    public ModelAndView getBestManZoneDeleteUser(Principal principal, @PathVariable final Long id) {
        ModelAndView mav;
        try {
            User user = this.giftService.getUserById(id, true);
            mav = new ModelAndView("bestmanzone/delete_user");
            mav.addObject("user", user);
            mav.addObject("principal", principal);
        } catch (ServiceException e) {
            mav = new ModelAndView("redirect:/bestmanzone/users");
        }
        mav.addObject("principal", principal);
        return mav;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = {"bestmanzone/delete_user/{id}"})
    public ModelAndView deleteBestManZoneDeleteUser(Principal principal, @PathVariable final Long id) {
        ModelAndView mav;
        try {
            this.giftService.deleteUserById(id);
        } catch (ServiceException e) {
        }
        mav = new ModelAndView("redirect:/bestmanzone/users");
        mav.addObject("principal", principal);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, value = {"pictures", "pictures/links"})
    public ModelAndView getPicturesLinks() {
        return new ModelAndView("pictures/links");
    }

    @RequestMapping(method = RequestMethod.GET, value = {"{any1}", "{any1}/{any2}"})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView defaultAction() {
        return new ModelAndView("error/notfound");
    }

    //fiks unit testing
}
