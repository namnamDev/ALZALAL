-- --------------------------------------------------------
-- 호스트:                          i5d205.p.ssafy.io
-- 서버 버전:                        10.3.31-MariaDB-0ubuntu0.20.04.1 - Ubuntu 20.04
-- 서버 OS:                        debian-linux-gnu
-- HeidiSQL 버전:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- ssafycommon 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `ssafycommon` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `ssafycommon`;

-- 테이블 ssafycommon.algorithm 구조 내보내기
CREATE TABLE IF NOT EXISTS `algorithm` (
  `algorithm_name` varchar(30) NOT NULL,
  PRIMARY KEY (`algorithm_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.algorithm_follow 구조 내보내기
CREATE TABLE IF NOT EXISTS `algorithm_follow` (
  `following_algorithm_no` varchar(255) NOT NULL,
  `follow_member_no` bigint(20) NOT NULL,
  PRIMARY KEY (`following_algorithm_no`,`follow_member_no`),
  KEY `FK79hm1uf8gwq4ydl7qn7gbvkur` (`follow_member_no`),
  CONSTRAINT `FK79hm1uf8gwq4ydl7qn7gbvkur` FOREIGN KEY (`follow_member_no`) REFERENCES `member` (`member_no`),
  CONSTRAINT `FK7isom43krvadchtkkvatxrwuy` FOREIGN KEY (`following_algorithm_no`) REFERENCES `algorithm` (`algorithm_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.article 구조 내보내기
CREATE TABLE IF NOT EXISTS `article` (
  `article_no` bigint(20) NOT NULL,
  `article_class` varchar(4) DEFAULT NULL,
  `article_content` varchar(15000) DEFAULT NULL,
  `article_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `article_title` varchar(500) DEFAULT NULL,
  `member_no` bigint(20) DEFAULT NULL,
  `problem_no` bigint(20) DEFAULT NULL,
  `problem_site_name` varchar(255) DEFAULT NULL,
  `use_language` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`article_no`),
  KEY `FK3hjpdfmeahavgrwhl2cjuj7f1` (`member_no`),
  KEY `FKbgv2uwpi0t88uhwwp6lqi667l` (`problem_no`,`problem_site_name`),
  KEY `FK9g7xcsyjji60a140j52xsou69` (`use_language`),
  CONSTRAINT `FK3hjpdfmeahavgrwhl2cjuj7f1` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`),
  CONSTRAINT `FK9g7xcsyjji60a140j52xsou69` FOREIGN KEY (`use_language`) REFERENCES `use_language` (`use_language`),
  CONSTRAINT `FKbgv2uwpi0t88uhwwp6lqi667l` FOREIGN KEY (`problem_no`, `problem_site_name`) REFERENCES `problem_site` (`problem_no`, `problem_site_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.article_algorithm 구조 내보내기
CREATE TABLE IF NOT EXISTS `article_algorithm` (
  `algorithm_name` varchar(255) NOT NULL,
  `article_no` bigint(20) NOT NULL,
  PRIMARY KEY (`algorithm_name`,`article_no`),
  KEY `FKmgts8flqlkb0xck4wnhuefj9g` (`article_no`),
  CONSTRAINT `FK8n6wqtki24j909pv59tlv0q2f` FOREIGN KEY (`algorithm_name`) REFERENCES `algorithm` (`algorithm_name`),
  CONSTRAINT `FKmgts8flqlkb0xck4wnhuefj9g` FOREIGN KEY (`article_no`) REFERENCES `article` (`article_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.article_comment 구조 내보내기
CREATE TABLE IF NOT EXISTS `article_comment` (
  `article_comment_no` bigint(20) NOT NULL,
  `article_comment_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `article_comment_content` varchar(3000) DEFAULT NULL,
  `article_no` bigint(20) DEFAULT NULL,
  `member_no` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`article_comment_no`),
  KEY `FKmugaa4sgm2uix0lewnpktax45` (`article_no`),
  KEY `FKnpu5qruwlvjd7qcmbqxssfj9h` (`member_no`),
  CONSTRAINT `FKmugaa4sgm2uix0lewnpktax45` FOREIGN KEY (`article_no`) REFERENCES `article` (`article_no`),
  CONSTRAINT `FKnpu5qruwlvjd7qcmbqxssfj9h` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.article_comment_like 구조 내보내기
CREATE TABLE IF NOT EXISTS `article_comment_like` (
  `article_comment_no` bigint(20) NOT NULL,
  `member_no` bigint(20) NOT NULL,
  PRIMARY KEY (`article_comment_no`,`member_no`),
  KEY `FK51bu81ptwl8cn9fgkkqqtfk4c` (`member_no`),
  CONSTRAINT `FK51bu81ptwl8cn9fgkkqqtfk4c` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`),
  CONSTRAINT `FKte9sigeeakf34m4sgr793uedb` FOREIGN KEY (`article_comment_no`) REFERENCES `article_comment` (`article_comment_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.article_like 구조 내보내기
CREATE TABLE IF NOT EXISTS `article_like` (
  `article_no` bigint(20) NOT NULL,
  `member_no` bigint(20) NOT NULL,
  PRIMARY KEY (`article_no`,`member_no`),
  KEY `FKtr6qqty6gh4m7p3lguveuu2d7` (`member_no`),
  CONSTRAINT `FKrp2po39s1rpa402v4gjuwjwp5` FOREIGN KEY (`article_no`) REFERENCES `article` (`article_no`),
  CONSTRAINT `FKtr6qqty6gh4m7p3lguveuu2d7` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.article_recomment 구조 내보내기
CREATE TABLE IF NOT EXISTS `article_recomment` (
  `article_recomment_no` bigint(20) NOT NULL,
  `article_content` varchar(3000) DEFAULT NULL,
  `article_recomment_date` tinyblob DEFAULT NULL,
  `article_comment_no` bigint(20) DEFAULT NULL,
  `member_no` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`article_recomment_no`),
  KEY `FKjh1k002nad7kwnliik58vishy` (`article_comment_no`),
  KEY `FKf4enflc58v0fd5i9xd4svj2wt` (`member_no`),
  CONSTRAINT `FKf4enflc58v0fd5i9xd4svj2wt` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`),
  CONSTRAINT `FKjh1k002nad7kwnliik58vishy` FOREIGN KEY (`article_comment_no`) REFERENCES `article_comment` (`article_comment_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.article_recomment_like 구조 내보내기
CREATE TABLE IF NOT EXISTS `article_recomment_like` (
  `article_recomment_no` bigint(20) NOT NULL,
  `member_no` bigint(20) NOT NULL,
  PRIMARY KEY (`article_recomment_no`,`member_no`),
  KEY `FKen19o59ps9fdkspk4b1euedlc` (`member_no`),
  CONSTRAINT `FKen19o59ps9fdkspk4b1euedlc` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`),
  CONSTRAINT `FKt2go1y89wvj0rxjjxq76op9u9` FOREIGN KEY (`article_recomment_no`) REFERENCES `article_recomment` (`article_recomment_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.discuss 구조 내보내기
CREATE TABLE IF NOT EXISTS `discuss` (
  `discuss_no` bigint(20) NOT NULL,
  `dsicuss_comp_name` varchar(200) DEFAULT NULL,
  `discuss_comp_problem` varchar(200) DEFAULT NULL,
  `discuss_date` datetime(6) DEFAULT NULL,
  `discuss_comp_host_no` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`discuss_no`),
  KEY `FKilq6l4l9awftp54h83n4hlx49` (`discuss_comp_host_no`),
  CONSTRAINT `FKilq6l4l9awftp54h83n4hlx49` FOREIGN KEY (`discuss_comp_host_no`) REFERENCES `discuss_host` (`discuss_comp_host_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.discuss_comment 구조 내보내기
CREATE TABLE IF NOT EXISTS `discuss_comment` (
  `discuss_comment_no` bigint(20) NOT NULL,
  `discuss_comment_content` varchar(3000) DEFAULT NULL,
  `discuss_comment_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `discuss_no` bigint(20) DEFAULT NULL,
  `member_no` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`discuss_comment_no`),
  KEY `FK6e0934sj3xmpgcnd1wmawfwpm` (`discuss_no`),
  KEY `FK6o2isus98m1rd4s85sy3cko7o` (`member_no`),
  CONSTRAINT `FK6e0934sj3xmpgcnd1wmawfwpm` FOREIGN KEY (`discuss_no`) REFERENCES `discuss` (`discuss_no`),
  CONSTRAINT `FK6o2isus98m1rd4s85sy3cko7o` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.discuss_comment_like 구조 내보내기
CREATE TABLE IF NOT EXISTS `discuss_comment_like` (
  `discuss_comment_no` bigint(20) NOT NULL,
  `member_no` bigint(20) NOT NULL,
  PRIMARY KEY (`discuss_comment_no`,`member_no`),
  KEY `FKrix2lwtkhjtrimrbgloqirhlj` (`member_no`),
  CONSTRAINT `FK7da4rxuyx947weosiw05l146u` FOREIGN KEY (`discuss_comment_no`) REFERENCES `discuss_comment` (`discuss_comment_no`),
  CONSTRAINT `FKrix2lwtkhjtrimrbgloqirhlj` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.discuss_host 구조 내보내기
CREATE TABLE IF NOT EXISTS `discuss_host` (
  `discuss_comp_host_no` bigint(20) NOT NULL,
  `discuss_comp_host_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`discuss_comp_host_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.discuss_recomment 구조 내보내기
CREATE TABLE IF NOT EXISTS `discuss_recomment` (
  `discuss_recomment_no` bigint(20) NOT NULL,
  `discuss_recomment_content` varchar(3000) DEFAULT NULL,
  `discuss_recomment_date` tinyblob DEFAULT NULL,
  `discuss_comment_no` bigint(20) DEFAULT NULL,
  `member_no` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`discuss_recomment_no`),
  KEY `FK302hbl2nqtxas5m886d77hx59` (`discuss_comment_no`),
  KEY `FKfnyf3c2xym0r1qtxiix0qutw` (`member_no`),
  CONSTRAINT `FK302hbl2nqtxas5m886d77hx59` FOREIGN KEY (`discuss_comment_no`) REFERENCES `discuss_comment` (`discuss_comment_no`),
  CONSTRAINT `FKfnyf3c2xym0r1qtxiix0qutw` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.discuss_recomment_like 구조 내보내기
CREATE TABLE IF NOT EXISTS `discuss_recomment_like` (
  `discuss_recomment_no` bigint(20) NOT NULL,
  `member_no` bigint(20) NOT NULL,
  PRIMARY KEY (`discuss_recomment_no`,`member_no`),
  KEY `FK4td2idd4wh4as22i54rgn7ma4` (`member_no`),
  CONSTRAINT `FK4td2idd4wh4as22i54rgn7ma4` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`),
  CONSTRAINT `FKrtygyby8cueqnfliyhqlvbmym` FOREIGN KEY (`discuss_recomment_no`) REFERENCES `discuss_recomment` (`discuss_recomment_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.helpme 구조 내보내기
CREATE TABLE IF NOT EXISTS `helpme` (
  `helpme_no` bigint(20) NOT NULL,
  `helpme_answer_article_no` bigint(20) DEFAULT NULL,
  `helpme_content` varchar(3000) DEFAULT NULL,
  `helpme_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `helpme_status` varchar(4) DEFAULT NULL,
  `helpme_receptor_no` bigint(20) DEFAULT NULL,
  `helpme_sender_no` bigint(20) DEFAULT NULL,
  `problem__no_helpme` bigint(20) DEFAULT NULL,
  `problem_site_name_helpme` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`helpme_no`),
  KEY `FKiq2n8n83ys92g6wyyjpq5qi6i` (`helpme_receptor_no`),
  KEY `FKlks6ijynhcqdpehkuhs4r2y01` (`helpme_sender_no`),
  KEY `FKioakl5qal9w29qicmujjo1npe` (`problem__no_helpme`,`problem_site_name_helpme`),
  CONSTRAINT `FKioakl5qal9w29qicmujjo1npe` FOREIGN KEY (`problem__no_helpme`, `problem_site_name_helpme`) REFERENCES `problem_site` (`problem_no`, `problem_site_name`),
  CONSTRAINT `FKiq2n8n83ys92g6wyyjpq5qi6i` FOREIGN KEY (`helpme_receptor_no`) REFERENCES `member` (`member_no`),
  CONSTRAINT `FKlks6ijynhcqdpehkuhs4r2y01` FOREIGN KEY (`helpme_sender_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.helpme_comment 구조 내보내기
CREATE TABLE IF NOT EXISTS `helpme_comment` (
  `helpme_recomment_no` bigint(20) NOT NULL,
  `helpme_comment_content` varchar(3000) DEFAULT NULL,
  `helpme_comment_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `helpme_no` bigint(20) DEFAULT NULL,
  `member_no` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`helpme_recomment_no`),
  KEY `FK7nqbso8e4er07u1sbk2vqn29p` (`helpme_no`),
  KEY `FK8mwspj9scxi0d4s63jitw7k5r` (`member_no`),
  CONSTRAINT `FK7nqbso8e4er07u1sbk2vqn29p` FOREIGN KEY (`helpme_no`) REFERENCES `helpme` (`helpme_no`),
  CONSTRAINT `FK8mwspj9scxi0d4s63jitw7k5r` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.helpme_comment_like 구조 내보내기
CREATE TABLE IF NOT EXISTS `helpme_comment_like` (
  `helpme_comment_no` bigint(20) NOT NULL,
  `member_no` bigint(20) NOT NULL,
  PRIMARY KEY (`helpme_comment_no`,`member_no`),
  KEY `FK48ibpq597td920aqjy1fur6s6` (`member_no`),
  CONSTRAINT `FK48ibpq597td920aqjy1fur6s6` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`),
  CONSTRAINT `FKk413le1ttprhclrvr95g4vrtl` FOREIGN KEY (`helpme_comment_no`) REFERENCES `helpme_comment` (`helpme_recomment_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.helpme_like 구조 내보내기
CREATE TABLE IF NOT EXISTS `helpme_like` (
  `helpme_no` bigint(20) NOT NULL,
  `member_no` bigint(20) NOT NULL,
  PRIMARY KEY (`helpme_no`,`member_no`),
  KEY `FKid3xxpk0d3br9idtmvvupau1f` (`member_no`),
  CONSTRAINT `FKid3xxpk0d3br9idtmvvupau1f` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`),
  CONSTRAINT `FKjg3ytqfghdlr0kevmcb479go2` FOREIGN KEY (`helpme_no`) REFERENCES `helpme` (`helpme_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.helpme_recomment 구조 내보내기
CREATE TABLE IF NOT EXISTS `helpme_recomment` (
  `helpme_recomment_no` bigint(20) NOT NULL,
  `helpme_recomment_content` varchar(3000) DEFAULT NULL,
  `helpme_recomment_date` tinyblob DEFAULT NULL,
  `helpme_comment_no` bigint(20) DEFAULT NULL,
  `member_no` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`helpme_recomment_no`),
  KEY `FK4q8d9s05bgrg74c44ohloq75m` (`helpme_comment_no`),
  KEY `FKo1wf0r08p4n13vt0c5k9btgyj` (`member_no`),
  CONSTRAINT `FK4q8d9s05bgrg74c44ohloq75m` FOREIGN KEY (`helpme_comment_no`) REFERENCES `helpme_comment` (`helpme_recomment_no`),
  CONSTRAINT `FKo1wf0r08p4n13vt0c5k9btgyj` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.helpme_recomment_like 구조 내보내기
CREATE TABLE IF NOT EXISTS `helpme_recomment_like` (
  `helpme_recomment_no` bigint(20) NOT NULL,
  `member_no` bigint(20) NOT NULL,
  PRIMARY KEY (`helpme_recomment_no`,`member_no`),
  KEY `FKayvkytdem8i7in7lq1k6yxm1b` (`member_no`),
  CONSTRAINT `FK3cccsixyyafitpjjhxfnr4aca` FOREIGN KEY (`helpme_recomment_no`) REFERENCES `helpme_recomment` (`helpme_recomment_no`),
  CONSTRAINT `FKayvkytdem8i7in7lq1k6yxm1b` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.hibernate_sequence 구조 내보내기
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_not_cached_value` bigint(21) NOT NULL,
  `minimum_value` bigint(21) NOT NULL,
  `maximum_value` bigint(21) NOT NULL,
  `start_value` bigint(21) NOT NULL COMMENT 'start value when sequences is created or value if RESTART is used',
  `increment` bigint(21) NOT NULL COMMENT 'increment value',
  `cache_size` bigint(21) unsigned NOT NULL,
  `cycle_option` tinyint(1) unsigned NOT NULL COMMENT '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
  `cycle_count` bigint(21) NOT NULL COMMENT 'How many cycles have been done'
) ENGINE=InnoDB SEQUENCE=1;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.member 구조 내보내기
CREATE TABLE IF NOT EXISTS `member` (
  `member_no` bigint(20) NOT NULL,
  `member_authority` varchar(255) DEFAULT 'ROLE_USER',
  `member_email` varchar(30) NOT NULL,
  `member_introduce` varchar(70) DEFAULT NULL,
  `member_name` varchar(20) NOT NULL,
  `notification_count` bigint(20) DEFAULT 0,
  `member_password` varchar(100) NOT NULL,
  `member_profile_img` varchar(255) DEFAULT NULL,
  `member_way` varchar(4) DEFAULT 'M00',
  PRIMARY KEY (`member_no`),
  UNIQUE KEY `UK_3orqjaukiw2b73e2gw8rer4rq` (`member_email`),
  UNIQUE KEY `UK_gwo2bqx6j18klv1e8ql503n61` (`member_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.member_follow 구조 내보내기
CREATE TABLE IF NOT EXISTS `member_follow` (
  `following_member_no` bigint(20) NOT NULL,
  `follow_member_no` bigint(20) NOT NULL,
  PRIMARY KEY (`following_member_no`,`follow_member_no`),
  KEY `FKib8y4t0es1jwomy7gfewsmsli` (`follow_member_no`),
  CONSTRAINT `FKhjkv3wd91e2m9wiwkvx9twtk3` FOREIGN KEY (`following_member_no`) REFERENCES `member` (`member_no`),
  CONSTRAINT `FKib8y4t0es1jwomy7gfewsmsli` FOREIGN KEY (`follow_member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.notification 구조 내보내기
CREATE TABLE IF NOT EXISTS `notification` (
  `notification_no` bigint(20) NOT NULL,
  `notification_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `notification_read_status` bit(1) DEFAULT NULL,
  `notification_subtask` varchar(4) DEFAULT NULL,
  `notification_target_no` bigint(20) DEFAULT NULL,
  `notification_task` varchar(3) DEFAULT NULL,
  `notification_reciever` bigint(20) DEFAULT NULL,
  `notification_sender` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`notification_no`),
  KEY `FKs91pus4dy5rpxc0qniivujimq` (`notification_reciever`),
  KEY `FKhixapg7lq8xqregdsoanlxfxb` (`notification_sender`),
  CONSTRAINT `FKhixapg7lq8xqregdsoanlxfxb` FOREIGN KEY (`notification_sender`) REFERENCES `member` (`member_no`),
  CONSTRAINT `FKs91pus4dy5rpxc0qniivujimq` FOREIGN KEY (`notification_reciever`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.problem_follow 구조 내보내기
CREATE TABLE IF NOT EXISTS `problem_follow` (
  `follow_member_no` bigint(20) NOT NULL,
  `following_problem_no` bigint(20) NOT NULL,
  `following_problem_site_name` varchar(255) NOT NULL,
  PRIMARY KEY (`following_problem_no`,`following_problem_site_name`,`follow_member_no`),
  KEY `FKqx8tnvai2i9yuqlxtcxk67q2w` (`follow_member_no`),
  CONSTRAINT `FKqg12lx57fgx5ikqr5rfl8hyg2` FOREIGN KEY (`following_problem_no`, `following_problem_site_name`) REFERENCES `problem_site` (`problem_no`, `problem_site_name`),
  CONSTRAINT `FKqx8tnvai2i9yuqlxtcxk67q2w` FOREIGN KEY (`follow_member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.problem_site 구조 내보내기
CREATE TABLE IF NOT EXISTS `problem_site` (
  `problem_no` bigint(20) NOT NULL,
  `problem_site_name` varchar(255) NOT NULL,
  `problem_site_link` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`problem_no`,`problem_site_name`),
  KEY `FK9211p1gcc3db4frn7vc8b99k6` (`problem_site_name`),
  CONSTRAINT `FK9211p1gcc3db4frn7vc8b99k6` FOREIGN KEY (`problem_site_name`) REFERENCES `problem_site_list` (`problem_site_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.problem_site_like 구조 내보내기
CREATE TABLE IF NOT EXISTS `problem_site_like` (
  `member_no` bigint(20) NOT NULL,
  `problem_site_name` varchar(255) NOT NULL,
  PRIMARY KEY (`member_no`,`problem_site_name`),
  KEY `FK1jr5yvy2j1j0p8upmn68nkkc9` (`problem_site_name`),
  CONSTRAINT `FK1jr5yvy2j1j0p8upmn68nkkc9` FOREIGN KEY (`problem_site_name`) REFERENCES `problem_site_list` (`problem_site_name`),
  CONSTRAINT `FKrlig4qnhrdxb8q332dnb4hjs` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.problem_site_list 구조 내보내기
CREATE TABLE IF NOT EXISTS `problem_site_list` (
  `problem_site_name` varchar(255) NOT NULL,
  PRIMARY KEY (`problem_site_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.use_language 구조 내보내기
CREATE TABLE IF NOT EXISTS `use_language` (
  `use_language` varchar(30) NOT NULL,
  PRIMARY KEY (`use_language`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 ssafycommon.use_language_like 구조 내보내기
CREATE TABLE IF NOT EXISTS `use_language_like` (
  `member_no` bigint(20) NOT NULL,
  `use_language` varchar(255) NOT NULL,
  PRIMARY KEY (`member_no`,`use_language`),
  KEY `FKhqhlsprck4jixgk4wuckryljp` (`use_language`),
  CONSTRAINT `FKhqhlsprck4jixgk4wuckryljp` FOREIGN KEY (`use_language`) REFERENCES `use_language` (`use_language`),
  CONSTRAINT `FKk7vfgo3m92rmjjoxxqx02c2j` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 내보낼 데이터가 선택되어 있지 않습니다.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
