### Configurer le projet 
- lancer les trois docker dans cicd/jenkins avec docker-compose
- aller sur localhost:8080 
- http://localhost:8080/manage/configureTools/ 
    - Maven installation (set la 3.9)

- créer un node avec 
    - name: "linux-agent"
    - remote roote directory : "/home/jenkins/agent"
    - save
    - recupérer son secret : le mettre dans /agent/linux/start-agent.sh


- installer les pluggin si besoin (git)

- créer un Item Mvn : le nommer comme on veut
    - restrict where this project can be run : linux-agent (nom de l'agent)
    - mettre le git avec les credential (ne pas oublier de mettre les autorisations)
    - mettre la branche (main ?)
    - je ne sais plus mais p-e : Build whenever a SNAPSHOT dependency is built
    - On passe les variables d'environnemenet un peut bourin comme ca, il existe un pluggin pour le faire mais ca ne marchait pas chez moi
        - Goals and options : clean test -Dmysql.host=mysql-db -Dmysql.port=3306 -Dmysql.database=bibliotheque -Dmysql.user=root -Dmysql.password=password
    - je ne sais plus non plus : Run regardless of build result : Should the post-build steps run only for successful builds, etc.
    - save

- on rebuild l'agent avec le bon secret
    - docker-compose stop jenkins-agent-linux 
    - docker-compose up -d jenkins-agent-linux

- normalement l'agent est online maintenant sur localhost:8080, on peut vérifier avec les logs :  `docker logs -f jenkins-agent-linux`

- on peut lancer un build normalement ca fonctionne si JESPERE je n'ai pas oublié une étape 

