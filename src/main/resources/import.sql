insert into provider (name, description)
values ('Princeton University',
        'Princeton University is a private research university located in Princeton, New Jersey, United States. It is one of the eight universities of the Ivy League, and one of the nine Colonial Colleges founded before the American Revolution.'),
       ('University of Michigan',
        'The mission of the University of Michigan is to serve the people of Michigan and the world through preeminence in creating, communicating, preserving and applying knowledge, art, and academic values, and in developing leaders and citizens who will challenge the present and enrich the future.'),
       ('Google',
        'Google Career Certificates are part of Grow with Google, an initiative that draws on Google''s 20-year history of building products, platforms, and services that help people and businesses grow. Through programs like these, we aim to help everyone– those who make up the workforce of today and the students who will drive the workforce of tomorrow – access the best of Google’s training and tools to grow their skills, careers, and businesses.'),
       ('University at Buffalo',
        'The University at Buffalo (UB) is a premier, research-intensive public university and the largest, most comprehensive institution of the State University of New York (SUNY) system. UB offers more than 100 undergraduate degrees and nearly 300 graduate and professional programs.');
insert into course (name, description, students_limit, provider_id)
values ('Algorithms, Part I',
        'This course covers the essential information that every serious programmer needs to know about algorithms and data structures, with emphasis on applications and scientific performance analysis of Java implementations. Part I covers elementary data structures, sorting, and searching algorithms. Part II focuses on graph- and string-processing algorithms. All the features of this course are available for free.  It does not offer a certificate upon completion.',
        5,
        1),
       ('Programming for Everybody (Getting Started with Python)',
        'This course aims to teach everyone the basics of programming computers using Python. We cover the basics of how one constructs a program from a series of simple instructions in Python.  The course has no pre-requisites and avoids all but the simplest mathematics. Anyone with moderate computer experience should be able to master the materials in this course. This course will cover Chapters 1-5 of the textbook “Python for Everybody”.  Once a student completes this course, they will be ready to take more advanced programming courses. This course covers Python 3.',
        3,
        2),
       ('PostgreSQL for Everybody Specialization',
        'Across these four courses, you’ll learn how to use the PostgreSQL database and explore topics ranging from database design to database architecture and deployment. You’ll also compare and contrast SQL and NoSQL approaches to database design. The skills in this course will be useful to learners doing data mining or application development.',
        2,
        2),
       ('Blockchain Basics',
        'This first course of the Blockchain specialization provides a broad overview of the essential concepts of blockchain technology – by initially exploring the Bitcoin protocol followed by the Ethereum protocol – to lay the foundation necessary for developing applications and programming. You will be equipped with the knowledge needed to create nodes on your personal Ethereum blockchain, create accounts, unlock accounts, mine, transact, transfer Ethers, and check balances.',
        6,
        4),
       ('Foundations of User Experience (UX) Design',
        'Foundations of User Experience (UX) Design is the first of a series of seven courses that will equip you with the skills needed to apply to entry-level jobs in user experience design. UX designers focus on the interactions that people have with products like websites, mobile apps, and physical objects. UX designers make those everyday interactions usable, enjoyable, and accessible. The role of an entry-level UX designer might include empathizing with users, defining their pain points, coming up with ideas for design solutions, creating wireframes, prototypes, and mockups, and testing designs to get feedback.',
        10,
        3);

insert into student (first_name, last_name)
values ('Mykhailo', 'Semitkin'),
       ('Ivan', 'Kolyvan'),
       ('Oleg', 'Nechytailo'),
       ('Barbara', 'Liskov'),
       ('Fabio', 'Wibmer');

insert into lecturer (first_name, last_name)
values ('Charles', 'Russell Severance'),
       ('Bina', 'Ramamurthy'),
       ('Robert', 'Dittmar'),
       ('Andrew', 'Wu'),
       ('Kevin', 'Wayne'),
       ('Robert', 'Sedgewick');

insert into course_lecturer (course_id, lecturer_id)
values (1, 5),
       (1, 6),
       (2, 1),
       (3, 1),
       (4, 3),
       (5, 3),
       (5, 4);

insert into student_course (course_id, student_id)
values (1, 1),
       (2, 1),
       (3, 1),
       (1, 2),
       (2, 2),
       (3, 2),
       (4, 3),
       (4, 4),
       (4, 5),
       (5, 5);
