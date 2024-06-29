package com.fenix.todoapp.navigation

import AddTodoScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fenix.todoapp.data.repository.TodoItemsRepository
import com.fenix.todoapp.di.addTodoComponent.DaggerAddTodoScreenComponent
import com.fenix.todoapp.di.app.AppComponent
import com.fenix.todoapp.di.app.DaggerAppComponent
import com.fenix.todoapp.di.todoItemsScreen.DaggerTodoItemsScreenComponent
import com.fenix.todoapp.ui.addTodoScreen.AddTodoScreenViewModel
import com.fenix.todoapp.ui.todoItemsScreen.TodoItemsScreenViewModel
import com.fenix.todoapp.ui.todoItemsScreen.composables.TodoItemsScreen

@Composable
fun Navigation() {

    val appComponent = remember {
        DaggerAppComponent.factory().create()
    }

    val todoItemsRepository = remember {
        appComponent.todoItemsRepository()
    }

    val navController = rememberNavController()

    val todoItemsScreenComponent = remember {
        DaggerTodoItemsScreenComponent.factory().create(navController, todoItemsRepository)
    }

    val addTodoItemsScreenComponent = remember {
        DaggerAddTodoScreenComponent.factory().create(navController, todoItemsRepository)
    }
    val todoItemsViewModel = remember {
        todoItemsScreenComponent.todoitemsViewModel()
    }





    NavHost(
        navController = navController,
        startDestination = Screen.TodoItemsScreen.route,
        ){
        composable(
            route = Screen.TodoItemsScreen.route,
        ){

            TodoItemsScreen(
                viewModel = todoItemsViewModel,
                )
        }

        composable(
            route = "${Screen.AddTodoScreen.route}/{todoid}",
            arguments = listOf(navArgument("todoid") { type = NavType.StringType; nullable = true })
        ){
            val addViewModel = remember {
                addTodoItemsScreenComponent.addItemsViewModel()
            }

            AddTodoScreen(
                viewModel = addViewModel,
                )
        }
    }




}
