package com.ttms.service.DistributorEntry;

import com.ttms.Entity.DisTourist;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IDistributorService {
    Void login(String distributorname, String password , HttpServletRequest request);

    List<DisTourist> getMySignUpTourist(Integer id);

    List<DisTourist> getMySignUpTourist(Integer id, Integer productId);
}
