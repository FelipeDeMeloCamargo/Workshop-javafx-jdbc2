package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	private DepartmentDao dao = DaoFactory.createDepartmentDao();
	
	public List<Department> findAll(){
		return dao.findAll();
		
	}
	
	public void saveOrUpdate(Department obj) {//inserir ou atualizar o department existente
		if(obj.getId() == null) { //pega o ID do OBJ e se ele for null, ele deverá inserir 
			dao.insert(obj);
		}
		else {
			dao.update(obj);//se ja tiver, ele somente atualiza
		}
		//ou insere ou atualiza
	}

	
}
