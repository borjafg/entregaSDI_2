package uo.sdi.business.impl.task;

import java.util.List;

import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.command.CommandExecutor;
import uo.sdi.business.impl.task.command.category.CreateCategoryCommand;
import uo.sdi.business.impl.task.command.category.DeleteCategoryCommand;
import uo.sdi.business.impl.task.command.category.DuplicateCategoryCommand;
import uo.sdi.business.impl.task.command.category.FindCategoriesByUserIdCommand;
import uo.sdi.business.impl.task.command.category.UpdateCategoryCommand;
import uo.sdi.business.impl.task.command.list_tasks.FindFinishedInboxByUserId;
import uo.sdi.business.impl.task.command.list_tasks.FindFinishedTasksByCategoryId;
import uo.sdi.business.impl.task.command.list_tasks.FindInboxTaskByUserId;
import uo.sdi.business.impl.task.command.list_tasks.FindTasksByCategoryId;
import uo.sdi.business.impl.task.command.list_tasks.FindTodayTasksByUserId;
import uo.sdi.business.impl.task.command.list_tasks.FindUnfinishedTasksByCategoryId;
import uo.sdi.business.impl.task.command.list_tasks.FindWeekTasksByUserId;
import uo.sdi.business.impl.task.command.task.CreateTaskCommand;
import uo.sdi.business.impl.task.command.task.DeleteTaskCommand;
import uo.sdi.business.impl.task.command.task.MarkTaskAsFinishedCommand;
import uo.sdi.business.impl.task.command.task.UpdateTaskCommand;
import uo.sdi.dto.CategoryDTO;
import uo.sdi.dto.DTOadapter;
import uo.sdi.dto.TaskDTO;
import uo.sdi.model.Task;
import uo.sdi.persistence.CategoryFinder;
import uo.sdi.persistence.TaskFinder;

public class TaskServiceImpl implements TaskService {

    // ======================================
    // Métodos relacionados con categorías
    // ======================================

    @Override
    public void createCategory(final CategoryDTO categ)
	    throws BusinessException {

	new CommandExecutor<Void>().execute(new CreateCategoryCommand(categ));
    }

    @Override
    public void duplicateCategory(final Long categId) throws BusinessException {
	new CommandExecutor<Void>().execute(new DuplicateCategoryCommand(
		categId));
    }

    @Override
    public void updateCategory(final CategoryDTO categ)
	    throws BusinessException {

	new CommandExecutor<Void>().execute(new UpdateCategoryCommand(categ));
    }

    @Override
    public void deleteCategory(final Long catId, final Long idUser)
	    throws BusinessException {

	new CommandExecutor<Void>().execute(new DeleteCategoryCommand(catId,
		idUser));
    }

    @Override
    public CategoryDTO findCategoryById(final Long id) throws BusinessException {
	return new CommandExecutor<CategoryDTO>()
		.execute(new Command<CategoryDTO>() {

		    @Override
		    public CategoryDTO execute() throws BusinessException {
			return DTOadapter.categoryToDTO(CategoryFinder
				.findById(id));
		    }
		});
    }

    @Override
    public List<CategoryDTO> findCategoriesByUserId(final Long userId)
	    throws BusinessException {

	return new CommandExecutor<List<CategoryDTO>>()
		.execute(new FindCategoriesByUserIdCommand(userId));
    }

    // =====================================
    // Métodos relacionados con tareas
    // =====================================

    @Override
    public void createTask(final TaskDTO task) throws BusinessException {
	new CommandExecutor<Task>().execute(new CreateTaskCommand(task));
    }

    @Override
    public void deleteTask(final Long taskId) throws BusinessException {
	new CommandExecutor<Void>().execute(new DeleteTaskCommand(taskId));
    }

    @Override
    public void markTaskAsFinished(final Long id) throws BusinessException {
	new CommandExecutor<Void>().execute(new MarkTaskAsFinishedCommand(id));
    }

    @Override
    public void updateTask(final TaskDTO task) throws BusinessException {

	new CommandExecutor<Void>().execute(new UpdateTaskCommand(task));
    }

    @Override
    public TaskDTO findTaskById(final Long taskId) throws BusinessException {
	return new CommandExecutor<TaskDTO>().execute(new Command<TaskDTO>() {

	    @Override
	    public TaskDTO execute() throws BusinessException {
		return DTOadapter.taskToDTO(TaskFinder.findById(taskId));
	    }
	});
    }

    // =====================================
    // Métodos para listar tareas
    // =====================================

    @Override
    public List<TaskDTO> findInboxTasksByUserId(final Long userId)
	    throws BusinessException {

	return new CommandExecutor<List<TaskDTO>>()
		.execute(new FindInboxTaskByUserId(userId));
    }

    @Override
    public List<TaskDTO> findWeekTasksByUserId(final Long userId)
	    throws BusinessException {

	return new CommandExecutor<List<TaskDTO>>()
		.execute(new FindWeekTasksByUserId(userId));
    }

    @Override
    public List<TaskDTO> findTodayTasksByUserId(final Long userId)
	    throws BusinessException {

	return new CommandExecutor<List<TaskDTO>>()
		.execute(new FindTodayTasksByUserId(userId));
    }

    @Override
    public List<TaskDTO> findTasksByCategoryId(final Long categId)
	    throws BusinessException {

	return new CommandExecutor<List<TaskDTO>>()
		.execute(new FindTasksByCategoryId(categId));
    }

    @Override
    public List<TaskDTO> findUnfinishedTasksByCategoryId(final Long categId)
	    throws BusinessException {

	return new CommandExecutor<List<TaskDTO>>()
		.execute(new FindUnfinishedTasksByCategoryId(categId));
    }

    @Override
    public List<TaskDTO> findFinishedTasksByCategoryId(final Long categId)
	    throws BusinessException {

	return new CommandExecutor<List<TaskDTO>>()
		.execute(new FindFinishedTasksByCategoryId(categId));
    }

    @Override
    public List<TaskDTO> findFinishedInboxTasksByUserId(final Long userId)
	    throws BusinessException {

	return new CommandExecutor<List<TaskDTO>>()
		.execute(new FindFinishedInboxByUserId(userId));
    }

}