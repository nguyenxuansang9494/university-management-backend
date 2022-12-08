## A back end for a university management application
This is my project for Java technology subject at University of Information Technology (Ho Chi Minh City).

### Goals:
* Build a maintainable, scalable management system
* Support managing following objects:
    * Student
    * Teacher
    * Faculty
    * Major
    * Subject
    * Grade
    * Schedule
    * Infrastructure


##### Subject dependency problem.
The system should behave like following expectations when it handles subjects and their prerequisite subjects:
* A subject is only allowed to be added into a curriculum if all of its prerequisite subject are in the curriculum.
* A subject is only allowed to be removed from a curriculum if all of its dependants are removed from the curriculum.
* A cycle of prerequisite subjects is not allowed.

