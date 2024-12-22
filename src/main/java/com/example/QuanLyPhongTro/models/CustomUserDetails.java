package com.example.QuanLyPhongTro.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
//@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Object user; // Có thể là User hoặc Admin

    public CustomUserDetails(Object user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user instanceof Admins) {
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (user instanceof Users) {
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return null;
    }

    public Object getUser() {
		return user;
	}

	public void setUser(Object user) {
		this.user = user;
	}

	@Override
    public String getPassword() {
        if (user instanceof Users) {
            return ((Users) user).getPassword();
        } else if (user instanceof Admins) {
            return ((Admins) user).getPassword();
        }
        return null;
    }

    @Override
    public String getUsername() {
        if (user instanceof Users) {
            return ((Users) user).getUsername();
        } else if (user instanceof Admins) {
            return ((Admins) user).getUsername();
        }
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}