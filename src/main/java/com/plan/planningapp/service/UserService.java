package com.plan.planningapp.service;

import com.plan.planningapp.model.Task;
import com.plan.planningapp.model.User;
import com.plan.planningapp.repositories.TaskRepository;
import com.plan.planningapp.repositories.UserRepository;
import dto.TaskDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    public void addUser(User u){
        userRepository.save(u);
    }

    public void addTaskToUserOwnedList(Task t, Integer id){

        //return Optional.ofNullable(person)
                //.map(p -> p.getLogo())
                //.orElse(new DefaultLogo());

        userRepository.findById(id).ifPresent(user->{
            user.becomeAnOwnerOfTheTask(t); userRepository.save(user);
        });

        //Optional<User> op = userRepository.findById(id).map(user->user.becomeAnOwnerOfTheTask(t));
        //.save(u);
    }

    public Iterable<TaskDto> getOwnedTasks(Integer id ) {
        ModelMapper modelMapper = new ModelMapper();

        Iterable<Task> tasks = taskRepository.findByOwnerUserId(id);
        ArrayList<TaskDto> tasksDto = new ArrayList<>();

        tasks.forEach(t->tasksDto.add(modelMapper.map(t, TaskDto.class)));

        return tasksDto;
        //return findByOwnerUserId(id);
    }

//    Iterable<Task> findByOwnerUserId(Integer id){
//        Iterable<Task> tasks = taskRepository.findAll();
//        ArrayList<Task> tas = new ArrayList<>();
//        tasks.forEach(t-> {if (t.getOwnerUser().getId() == id) tas.add(t);});
//        return tas;
//    }

}