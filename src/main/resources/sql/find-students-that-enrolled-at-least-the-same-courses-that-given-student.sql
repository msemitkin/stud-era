select student.id, student.first_name, student.last_name
from student
where not exists(select course_id
                 from student_course
                 where student_id = :id
                   and course_id not in
                       (select course_id from student_course where student_id = student.id and student_id <> :id));