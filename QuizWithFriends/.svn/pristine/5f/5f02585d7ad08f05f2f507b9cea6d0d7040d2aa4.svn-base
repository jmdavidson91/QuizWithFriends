USE c_cs108_ajaustin;

DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS quest_long_answer;
DROP TABLE IF EXISTS quest_fill_blank;
DROP TABLE IF EXISTS quest_multiple_choice;
DROP TABLE IF EXISTS quest_picture_response;
DROP TABLE IF EXISTS quest_multiple_response;
DROP TABLE IF EXISTS quest_multiresponse_multianswer;
DROP TABLE IF EXISTS quest_matching;
 -- remove table if it already exists and start from scratch

CREATE TABLE questions (
	id INTEGER AUTO_INCREMENT, 
	question_type TEXT, 
	question_index INTEGER,
	PRIMARY KEY (id)
	);
CREATE TABLE quest_long_answer (
	id INTEGER AUTO_INCREMENT, 
	question TEXT,  
	answer TEXT, 
	PRIMARY KEY (id)
);
CREATE TABLE quest_fill_blank (
	id INTEGER AUTO_INCREMENT, 
	first_part_question TEXT,  
	answer TEXT, 
	second_part_question TEXT, 
	PRIMARY KEY (id)
);
CREATE TABLE quest_multiple_choice (
	id INTEGER AUTO_INCREMENT, 
	question TEXT,  
	answer TEXT, 
	one_other_poss TEXT, 
	two_other_poss TEXT, 
	three_other_poss TEXT, 
	PRIMARY KEY (id)
);
CREATE TABLE quest_picture_response (
	id INTEGER AUTO_INCREMENT, 
	question TEXT,  
	picture TEXT, 
	answer TEXT,
	PRIMARY KEY (id)
);
CREATE TABLE quest_multiple_response (
	id INTEGER AUTO_INCREMENT, 
	question TEXT,  
	answers TEXT, 
	ordered BOOL,
	number_correct INTEGER,
	PRIMARY KEY (id)
);
CREATE TABLE quest_multiresponse_multianswer (
	id INTEGER AUTO_INCREMENT, 
	question TEXT,  
	answers TEXT, 
	others TEXT,
	PRIMARY KEY (id)
);
CREATE TABLE quest_matching (
	id INTEGER AUTO_INCREMENT, 
	prompts TEXT, 
	answers TEXT,
	PRIMARY KEY (id)
);



