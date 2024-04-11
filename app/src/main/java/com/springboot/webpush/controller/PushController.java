package com.springboot.webpush.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.webpush.controller.model.Notification;
import com.springboot.webpush.controller.model.PushKey;
import com.springboot.webpush.exception.PushkeyNotFoundException;
import com.springboot.webpush.service.NotificationPushService;

@RestController
@RequestMapping("/api/key")
public class PushController {

	private final NotificationPushService notificationPushService;

	/**
	 * @param notificationPushService
	 */
	public PushController(final NotificationPushService notificationPushService) {
		this.notificationPushService = notificationPushService;
	}

	@GetMapping
	public PushKey get() {
		return notificationPushService.getPushkey().orElseThrow(PushkeyNotFoundException::new);
	}

	@PostMapping
	PushKey update(@RequestBody final PushKey pushKey) {
		notificationPushService.setPushkey(pushKey);
		return get();
	}

	@PutMapping
	void update(@RequestBody final Notification notification) {
		notificationPushService.send(notification.getPayload());
	}
}
