package com.dm.retail.repository;

import com.dm.retail.enums.UserType;

public interface UserRepository {
    String getUserTypeByUsername(String username);
}
