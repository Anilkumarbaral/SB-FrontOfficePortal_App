package ins.ashokit.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ins.ashokit.Entity.CourseEntity;
import ins.ashokit.Entity.EnqStatusEntity;
import ins.ashokit.repo.CourseRepo;
import ins.ashokit.repo.EnqStatusRepo;

@Component
public class DataLoader implements ApplicationRunner {
	
	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private EnqStatusRepo enqStatusRepo;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		  courseRepo.deleteAll();
		  
		  CourseEntity c=new CourseEntity();
		  c.setCourseName("java");
		  CourseEntity c1=new CourseEntity();
		  c1.setCourseName("python");
		  CourseEntity c3=new CourseEntity();
		  c3.setCourseName("DevOps");
		  
		  courseRepo.saveAll(Arrays.asList(c,c1,c3));
		  
		  enqStatusRepo.deleteAll();
		  EnqStatusEntity e=new EnqStatusEntity();
		  e.setStatusName("Enrolled");
		  EnqStatusEntity e1=new EnqStatusEntity();
		  e1.setStatusName("New");
		  EnqStatusEntity e2=new EnqStatusEntity();
		  e2.setStatusName("Lost");
		  
		  enqStatusRepo.saveAll(Arrays.asList(e,e1,e2));
		
	}
	
	

}
