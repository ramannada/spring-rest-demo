package com.ramannada.springdemo.security;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

public interface Authentication extends Principal, Serializable {
    Object getPrincipal();

    Object getCredentials();

    Object getDetails();

    Collection<? extends GrantedAuthority> getAuthorities();

    boolean isAuthenticated();

    void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;
}
