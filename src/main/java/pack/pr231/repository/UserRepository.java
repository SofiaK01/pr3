package pack.pr231.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pack.pr231.model.User;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Transactional
    @Query("update User u set u.email = :email, u.pts = :pts, u.nickname = :nickname where u.id = :id")
    int update(@Param("id") int id, @Param("email") String email, @Param("nickname") String nickname, @Param("pts") int pts);

    @Transactional
    User findUserByNickname(String nickname);


}


