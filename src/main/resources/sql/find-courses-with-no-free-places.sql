select course.id,
       course.name,
       course.description,
       course.students_limit,
       p.id          as provider_id,
       p.name        as provider_name,
       p.description as provider_description
from course
         join provider p on p.id = course.provider_id
where provider_id = :id
  and course.students_limit <= (select count(*) from student_course where course_id = course.id);