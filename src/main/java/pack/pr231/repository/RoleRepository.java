package pack.pr231.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pack.pr231.model.Role;

import javax.transaction.Transactional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Transactional
    Role findRoleByName(String name);
}
