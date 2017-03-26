package uo.sdi.business;

import java.util.List;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.CategoryDTO;
import uo.sdi.dto.TaskDTO;

public interface TaskService {

    // ======================================
    // Métodos relacionados con categorías
    // ======================================

    public void createCategory(CategoryDTO category) throws BusinessException;

    public void duplicateCategory(Long id) throws BusinessException;

    public void updateCategory(CategoryDTO category) throws BusinessException;

    public void deleteCategory(Long idCategory, Long idUser)
	    throws BusinessException;

    public CategoryDTO findCategoryById(Long id) throws BusinessException;

    public List<CategoryDTO> findCategoriesByUserId(Long id)
	    throws BusinessException;

    // =====================================
    // Métodos relacionados con tareas
    // =====================================

    public void createTask(TaskDTO task) throws BusinessException;

    public void deleteTask(Long id) throws BusinessException;

    public void markTaskAsFinished(Long id) throws BusinessException;

    public void updateTask(TaskDTO task) throws BusinessException;

    public TaskDTO findTaskById(Long id) throws BusinessException;

    // =====================================
    // Métodos para listar tareas
    // =====================================

    public List<TaskDTO> findInboxTasksByUserId(Long id)
	    throws BusinessException;

    public List<TaskDTO> findWeekTasksByUserId(Long id)
	    throws BusinessException;

    public List<TaskDTO> findTodayTasksByUserId(Long id)
	    throws BusinessException;

    public List<TaskDTO> findTasksByCategoryId(Long catId)
	    throws BusinessException;

    public List<TaskDTO> findUnfinishedTasksByCategoryId(Long catId)
	    throws BusinessException;

    public List<TaskDTO> findFinishedTasksByCategoryId(Long catId)
	    throws BusinessException;

    public List<TaskDTO> findFinishedInboxTasksByUserId(Long userId)
	    throws BusinessException;

}