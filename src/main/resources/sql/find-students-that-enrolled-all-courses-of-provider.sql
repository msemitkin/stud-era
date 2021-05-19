select student.id, student.first_name, student.last_name
from student
where not exists((select course.id
                  from course
                           join provider p on p.id = course.provider_id
                  where provider_id = :id
                    and course.id not in (
                      select course_id
                      from student_course
                      where student_id = student.id
                        and course_id = course.id)))