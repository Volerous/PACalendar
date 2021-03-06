"use strict";
exports.__esModule = true;
var angular = require("angular");
var moment = require("moment");
var app = angular.module("PA-App", ["ngMaterial", "ngMessages", "mdPickers"]);
app.directive("customChip", function () {
    return {
        restrict: "EA",
        link: function (scope, elem, attrs) {
            var chipTemplateClass = attrs["class"];
            elem.removeClass(chipTemplateClass);
            var mdChip = elem.parent().parent();
            if (scope.$chip.color) {
                mdChip[0].style.setProperty("background", "#" + scope.$chip.color);
            }
            mdChip.addClass(chipTemplateClass);
        }
    };
});
app.service("$todoservice", function ($http, $mdDialog) {
    var todoList = [];
    this.state;
    this.viewElement;
    this.baseTodo = {
        title: "",
        due_date: null,
        color: "purple",
        completed: false,
        priority: 3,
        tags: [],
        description: ""
    };
    this.updateListTodos = function () {
        $http({
            url: "/_todo",
            method: "GET"
        }).then(function (data) {
            for (var i = 0; i < data.data.todos.length; i++) {
                todoList.push(data.data.todos[i]);
            }
        }, function (data) {
            console.log(data);
        });
    };
    this.checkTodo = function (todo) {
        return $http({
            url: "/_todo",
            method: "PUT",
            data: todo
        });
    };
    this.deleteTodo = function (ev, id) {
        $http({
            url: "/_todo/" + id,
            method: "DELETE"
        }).then(function (data) {
            console.log(data);
        }, function (data) {
            console.log("error");
        });
    };
    this.createTodo = function (ev) {
        this.state = 1;
        this.viewElement = {
            title: "",
            due_date: null,
            color: "purple",
            completed: false,
            priority: 3,
            tags: [],
            description: ""
        };
        return $mdDialog.show({
            controller: "EventCtrl",
            templateUrl: "create_todo.html",
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: false
        });
    };
    this.viewTodo = function (ev, todo) {
        this.state = 0;
        this.viewElement = todo;
        return $mdDialog.show({
            controller: "EventCtrl",
            templateUrl: "create_todo.html",
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: false
        });
    };
    this.insertTodo = function (todo) {
        var retval;
        $http({
            url: "/_todo",
            method: "POST",
            headers: { "Content-Type": "application/json" },
            data: JSON.stringify(todo)
        }).then(function (success) {
            retval = success.data.id;
        }, function (data) {
            console.log("error");
            console.log(data);
        });
        return retval;
    };
    this.editTodo = function (todo) {
        $http({
            url: "/_todo",
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            data: JSON.stringify(todo)
        }).then(function (success) { }, function () { });
    };
    this.updateListTodos();
    this.todoList = todoList;
});
app.service("$colorService", function ($mdColorPalette) {
    this.colors = [];
    this.createColors = function () {
        for (var color in $mdColorPalette) {
            this.colors.push(color.replace("-", " "));
        }
    };
    this.createColors();
});
app.service("$tagService", function ($http) {
    this.find_by_part = function (part) {
        return $http.get("/_tag/" + part).then(function (data) {
            return data.data.ret;
        });
    };
    this.tagsList = [];
    this.updateTagList = function () {
        this.tagsList = $http.get("/_tag").then(function (data) {
            return data.data.ret;
        });
    };
});
app.config([
    "$interpolateProvider",
    function ($interpolateProvider) {
        $interpolateProvider.startSymbol("{|");
        $interpolateProvider.endSymbol("|}");
    }
]);
app.config(function ($mdThemingProvider, $mdColorPalette) {
    $mdThemingProvider.alwaysWatchTheme(true);
    angular.forEach(Object.keys($mdColorPalette), function (color) {
        $mdThemingProvider.theme(color.replace("-", " ")).backgroundPalette(color);
    });
});
app.controller("MainCtrl", function ($scope, $mdDialog, $todoservice, $mdToast, $mdColorUtil, $mdColors, $http, $interval, $mdMenu, $tagService) {
    $scope.customFullscreen = false;
    $scope.todos = $todoservice.todoList;
    $scope.tags = $tagService.tagList;
    $scope.getColor = function (color) {
        if (color !== null) {
            return color;
        }
        else {
            return $mdColorUtil.rgbaToHex($mdColors.getThemeColor("purple-background-500"));
        }
    };
    $scope.deleteTodo = function (ev, todo) {
        // Appending dialog to document.body to cover sidenav in docs app
        var confirm = $mdDialog
            .confirm()
            .title("Delete " + todo.title + "?")
            .ariaLabel("Delete Task")
            .targetEvent(ev)
            .ok("Delete Task")
            .cancel("Cancel");
        $mdDialog.show(confirm).then(function () {
            $todoservice.deleteTodo(ev, todo.id);
            var eleToRemove = $scope.todos.indexOf(todo);
            $scope.showToast("Task Deleted");
            $scope.todos.splice(eleToRemove, 1);
        }, function () { });
    };
    $scope.showToast = function (info) {
        $mdToast.show($mdToast
            .simple()
            .textContent(info)
            .position("bottom right")
            .hideDelay(3000));
    };
    $scope.checkTodo = function (ev, todo) {
        $todoservice.checkTodo(todo).then(function () {
            var eleToRemove = $scope.todos.indexOf(todo);
            // $scope.todos.splice(eleToRemove, 1);
            $scope.showToast("Task marked as comeplete");
        }, function () {
            $scope.showToast("Task not marked as complete");
        });
    };
    $scope.checkDate = function (date) {
        var weekdays = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
        var numDays = moment(date).diff(moment(), "days") + 1;
        if (date !== null && numDays >= 1) {
            if (numDays === 1) {
                return "Tomorrow";
            }
            else if (numDays <= 6) {
                return weekdays[moment(date).day()];
            }
            return "in " + numDays + " days";
        }
        else if (date !== null) {
            return moment(date).format("D-MM-YY");
        }
    };
    $scope.viewTodo = function (ev, todo) {
        var index = $scope.todos.indexOf(todo);
        $todoservice.viewTodo(ev, todo).then(function (todo) {
            $scope.todos[index] = todo;
        }, function () { });
    };
    $scope.createEvent = function (ev) {
        $mdDialog.show({
            controller: "DemoCtrl",
            templateUrl: "create_event.html",
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: false,
            fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
        });
    };
    $scope.createToDo = function (ev) {
        $todoservice.createTodo(ev).then(function (todo) {
            $scope.todos.push(todo);
        }, function () { });
        $todoservice.viewElement = {
            title: "",
            due_date: null,
            color: "purple",
            completed: false,
            priority: 3,
            tags: [],
            description: ""
        };
    };
    $scope.createTag = function (ev) {
        $mdDialog
            .show({
            controller: "TagCtrl",
            templateUrl: "create_tag.html",
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: false,
            fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
        })
            .then(function (todo_obj) {
            $scope.todos.push(todo_obj);
        }, function () { });
    };
    $scope.checkNotification = function () {
        $http.get("/_check_notifications").then(function (data) {
            $scope.notifications = data.data;
        });
    };
    $interval($scope.checkNotification, 60000);
    $scope.regex = /#(\w+)|@(\w+:\w+|\w+)|%([1-5])/gi;
    $scope.quickAddSearch = function (find) {
        if (find) {
            $scope.m = find.match($scope.regex);
            console.log($scope.m);
            if ($scope.m.length > 0 && $scope.m[$scope.m.length - 1].indexOf("#") !== -1) {
                return $tagService.find_by_part($scope.m[$scope.m.length - 1].substr(1));
            }
        }
        else {
            return [];
        }
    };
    $scope.searchTextPH = "";
    $scope.selChange = function (text) {
        console.log($scope.m[$scope.m.length - 1].substr(1).length);
        $scope.searchTextPH = $scope.searchTextPH.slice(0, -($scope.m[$scope.m.length - 1].substr(1).length));
        $scope.searchText = $scope.searchTextPH + $scope.selectedItem.title;
        console.log($scope.searchText);
    };
    $scope.seaChange = function (text) {
        $scope.searchTextPH = text;
    };
});
app.controller("DemoCtrl", function ($scope, $colorService) {
    $scope.user = {
        title: "Developer",
        email: "ipsum@lorem.com",
        colorChoice: "purple",
        tags: []
    };
    $scope.colors = $colorService.colors;
});
app.controller("EventCtrl", function ($scope, $colorService, $mdDialog, $todoservice, $tagService, $http) {
    $scope.selectedItem = "";
    $scope.searchText = "";
    $scope.transformChip = function (chip) {
        if (angular.isObject(chip)) {
            return chip;
        }
        return { title: chip, type: "new" };
    };
    $scope.querySearch = function (find) {
        if (find) {
            return $tagService.find_by_part(find);
        }
        else {
            return [];
        }
    };
    $scope.state = $todoservice.state;
    if ($todoservice.state >= 1) {
        $scope.disabled = false;
    }
    else {
        $scope.disabled = true;
    }
    $scope.todo = $todoservice.viewElement;
    $scope.colors = $colorService.colors;
    $scope.done = function () {
        if ($scope.todo.title !== "") {
            $scope.todo.due_date = moment($scope.todo.due_date);
            $scope.todo.id = $todoservice.insertTodo($scope.todo);
            $mdDialog.hide($scope.todo);
        }
    };
    $scope.cancel = function () {
        $mdDialog.cancel();
    };
    $scope.edit = function () {
        $scope.state = 2;
        $scope.disabled = false;
    };
    $scope.editCancel = function () {
        $scope.todo = $todoservice.viewElement;
        $mdDialog.hide($scope.todo);
    };
    $scope.editDone = function () {
        $todoservice.editTodo($scope.todo);
        $mdDialog.hide($scope.todo);
    };
    $scope.viewBack = function () {
        $mdDialog.hide($scope.todo);
    };
});
app.controller("TagCtrl", function ($scope, $http, $colorService) {
    $scope.tag = {
        title: "",
        color: "purple"
    };
    $scope.colors = $colorService.colors;
});
