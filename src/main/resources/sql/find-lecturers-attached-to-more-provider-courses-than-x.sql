select lecturer.id, lecturer.first_name, lecturer.last_name
from lecturer
where (select count(*)
       from lecturer
                join course_lecturer cl on lecturer.id = cl.lecturer_id
                join course c on c.id = cl.course_id
                join provider p on p.id = c.provider_id
       where provider_id = :id) > :count