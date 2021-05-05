package com.waveaccess.condominiumapi.controllers.moderator;

import com.waveaccess.condominiumapi.dto.UserDto;
import com.waveaccess.condominiumapi.mappers.UserMapper;
import com.waveaccess.condominiumapi.models.User;
import com.waveaccess.condominiumapi.services.interfaces.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Api("Moderating")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('MODERATOR')")
@RequestMapping(ClientModerationController.ROOT_URL)
public class ClientModerationController {

    public static final String ROOT_URL = "/v1/moder/";
    public static final String PAGE_URL = "/page";

    private final UserService userService;
    private final UserMapper userMapper;

    @ApiOperation("Get inactive users")
    @ApiResponses({
            @ApiResponse(
                    code = SC_BAD_REQUEST,
                    message = "Account or password not specified: \"empty-param\"; " +
                            "account param has invalid email: \"invalid-email\""),
            @ApiResponse(
                    code = SC_UNAUTHORIZED,
                    message = "Authentication failed"
            )
    })
    @GetMapping(PAGE_URL)
    public Page<UserDto> getInactivePage(Pageable pageable) {
        return userService.getPage(pageable).map(userMapper::userToDto);
    }

    @ApiOperation("Set user as active")
    @PostMapping
    public User setUserAsActive(Long id) {
        return userService.setActive(id);
    }
}