package com.example.demo.api.controllers.User;

import com.example.demo.Model.Address;
import com.example.demo.Model.Repo.AddressRepo;
import com.example.demo.Model.User;
import com.example.demo.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private AddressRepo addressRepo;
@Autowired
public UserController(AddressRepo addressRepo, UserService userService) {
    this.addressRepo = addressRepo;
    this.userService = userService;
}

    @GetMapping("/{userId}/address")
    public ResponseEntity<List<Address>> getUserAddresses(@AuthenticationPrincipal User user,
                                                          @PathVariable long userId) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (user.getId() != userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(addressRepo.findByUser_Id(userId));
    }

    @PutMapping("/{userId}/address")
    public ResponseEntity<Address> putAddress(@AuthenticationPrincipal User user , @PathVariable long userId, @RequestBody Address address) {
    if(!userService.userHasPermisson(user , userId)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    address.setId(null);
    User refuser = new User();
    refuser.setId(userId);
    address.setUser(refuser);
   return ResponseEntity.ok( addressRepo.save(address));
}

    @PatchMapping("/{userId}/address/{addressId}")
    public ResponseEntity<Address> patchAddress(@AuthenticationPrincipal User user , @PathVariable long userId, @PathVariable long addressId, @RequestBody Address address) {
        if(!userService.userHasPermisson(user , userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (addressId == address.getId()) {
            Optional<Address> addressOptional = addressRepo.findById(addressId);
            if (addressOptional.isPresent()) {
                User user1= addressOptional.get().getUser();
                if (user1.getId() != userId) {
                   return ResponseEntity.ok(addressRepo.save(address)) ;
                }
            }
        }
        return  ResponseEntity.badRequest().build();
    }


}
