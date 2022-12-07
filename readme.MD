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
The system should behave like following expectations when it handle subjects and their prerequisite subjects:
* when a subject is added into a curriculum, all of its prerequisite subjects must also be added into the curriculum.
* when a subject is removed from a curriculum, all subjects depend on it should also be deleted.
* when a subject is removed from a curriculum, it is optional to remove its dependencies which are not other subjects' dependency.
* a circle of prerequisite subjects is not allowed.

