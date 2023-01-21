package com.omnichannel.app.model.usermanagement;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class MyUserDetails implements UserDetails {

    /**
     * Implement Authority
     */
    private static final long serialVersionUID = 1L;

    private User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<AdhocPrivilege> adhocPrivilege = user.getAdhocPrivilege();
        Set<AdhocViewPrivilege> adhocViewPrivilege = user.getAdhocViewPrivilege();
        Collection<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            Collection<ViewPrivilege> viewPrivilege = role.getViewPrivilege();
            Collection<Privilege> privileges = role.getPrivileges();

            for (Privilege privilege : privileges) {
                authorities.add(new SimpleGrantedAuthority(privilege.getName()));
            }
            for (ViewPrivilege privilege : viewPrivilege) {
                authorities.add(new SimpleGrantedAuthority(privilege.getPrivilage()));
            }
        }

        for (AdhocPrivilege adhoc : adhocPrivilege) {
            authorities.add(new SimpleGrantedAuthority(adhoc.getName()));
        }
        for (AdhocViewPrivilege adhoc : adhocViewPrivilege) {
            authorities.add(new SimpleGrantedAuthority(adhoc.getPrivilage()));
        }

        System.out.println(authorities.toString());

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNotExpired();
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
        return user.isEnabled();
    }

    public String profilePicLink() {
        return user.getProfilePicLink();
    }

}
