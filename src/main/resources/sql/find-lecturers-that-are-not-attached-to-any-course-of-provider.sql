select distinct lecturer.id, lecturer.first_name, lecturer.last_name
from lecturer
where lecturer.id not in (select lecturer_id
                          from course_lecturer
                                   join course c on c.id = course_lecturer.course_id
                                   join provider p on p.id = c.provider_id
                          where provider_id = :id);