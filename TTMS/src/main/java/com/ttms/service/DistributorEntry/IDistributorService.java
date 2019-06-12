package com.ttms.service.DistributorEntry;

public interface IDistributorService {
    Void login(String distributorname, String password);

    List<DisTourist> getMySignUpTourist(Integer id);
}
