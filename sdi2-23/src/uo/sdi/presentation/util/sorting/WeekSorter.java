package uo.sdi.presentation.util.sorting;

import uo.sdi.dto.TaskDTO;

public class WeekSorter {

    public int sortAtInit(Object task_1, Object task_2) {
	TaskDTO task1 = (TaskDTO) task_1;
	TaskDTO task2 = (TaskDTO) task_2;

	return task1.getTitle().compareTo(task2.getTitle());
    }

}