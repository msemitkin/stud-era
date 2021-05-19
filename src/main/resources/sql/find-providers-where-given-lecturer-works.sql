select distinct p.id, p.name, p.description
from lecturer
         join course_lecturer cl on lecturer.id = cl.lecturer_id
         join course c on c.id = cl.course_id
         join provider p on p.id = c.provider_id
where lecturer_id = :id;