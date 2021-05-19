select distinct student.id, student.first_name, student.last_name
from student
         join student_course sc on student.id = sc.student_id
         join course c on c.id = sc.course_id
         join provider p on p.id = c.provider_id
where provider_id = :id;