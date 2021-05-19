select avg(c.students_limit)
from provider
         join course c on provider.id = c.provider_id
where provider_id = :id;