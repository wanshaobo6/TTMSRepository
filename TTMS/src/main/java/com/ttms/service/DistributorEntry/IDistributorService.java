package com.ttms.service.DistributorEntry;

import javax.servlet.http.HttpServletRequest;

public interface IDistributorService {
    Void login(String distributorname, String password, HttpServletRequest request);
}
