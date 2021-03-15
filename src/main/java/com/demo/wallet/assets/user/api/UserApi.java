package com.demo.wallet.assets.user.api;

import com.demo.wallet.assets.user.User;
import com.demo.wallet.assets.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserApi {

    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserTo create(@Valid  @RequestBody @NonNull UserTo userTo) {
        var mapper = new ModelMapper();
        var user = userService.save(mapper.map(userTo, User.class));
        return mapper.map(user, UserTo.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NonNull Long id) {
        userService.delete(id);
    }

}
