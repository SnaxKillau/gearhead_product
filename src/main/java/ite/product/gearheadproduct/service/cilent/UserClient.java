package ite.product.gearheadproduct.service.cilent;

import ite.product.gearheadproduct.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "identity-service")
public interface UserClient {
    @GetMapping("/auth/userDetail")
    UserResponse getUserDetail(@RequestParam("id") String id);
}
