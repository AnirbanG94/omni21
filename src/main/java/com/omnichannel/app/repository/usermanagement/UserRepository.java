package com.omnichannel.app.repository.usermanagement;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.omnichannel.app.model.usermanagement.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsernameAndEmailAndMobile(String username, String email, String mobile);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByVendorId(Integer vendorId);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    public User getUserByUsername(@Param("username") String username);

    @Query(value = "select * from usm_user where ishod is true", nativeQuery = true)
    public List<User> getHodList();

    @Query(value = "SELECT * FROM usm_user WHERE account_not_expired IS true AND enabled IS true", nativeQuery = true)
    public List<User> getAllAictiveList();

    Optional<User> findByVendorIdAndEnabledAndAccountNotExpired(Integer vendorId, boolean enabled,
            boolean accountNotExpired);
}
