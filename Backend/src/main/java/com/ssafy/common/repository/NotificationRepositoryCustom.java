package com.ssafy.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.ssafy.common.dto.NotificationDTO;

public interface NotificationRepositoryCustom {

	Optional<List<NotificationDTO>> notiList(Long memberNo, Pageable page);

	Long readNoti(Long notiNo);

}
