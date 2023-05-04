package ins.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ins.ashokit.Entity.UserdtlsEntity;

public interface UserDtlsRepo extends JpaRepository<UserdtlsEntity, Integer>{
	
	 public  UserdtlsEntity findByEmail(String email);
	 public UserdtlsEntity findByEmailAndPwd(String email,String pwd);

}
