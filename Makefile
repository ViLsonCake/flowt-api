startapp:
	mvn clean package
	docker-compose up --build
bashdb:
	docker exec -it song_storage bash
logsapp:
	docker logs flowt_app
rmvolume:
	docker rm song_storage
	docker volume rm flowt_song_storage