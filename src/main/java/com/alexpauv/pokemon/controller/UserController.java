package com.alexpauv.pokemon.controller;

import com.alexpauv.pokemon.dto.user.UserAddOrRemoveRoleRequest;
import com.alexpauv.pokemon.dto.user.UserDto;
import com.alexpauv.pokemon.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PatchMapping("/{uuid}/role")
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public ResponseEntity<Void> addOrRemoveRole(@PathVariable String uuid, @RequestBody UserAddOrRemoveRoleRequest request) {
        userService.addOrRemoveUserRole(uuid, request);
        return ResponseEntity.noContent().build();
    }
}
