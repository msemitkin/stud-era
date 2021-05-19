select course.id, course.name, course.description, co
from course
         join provider p on p.id = course.provider_id
where provider_id = :id
  and course.students_limit <= (select count(*) from student_course where course_id = course.id);