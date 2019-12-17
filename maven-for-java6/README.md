pom.xml 설정만으로는 Java6 환경에서 돌릴 수 없고 메이븐 자체의 버전을 낮춰야함.

[메이븐 3.2.5 다운로드](https://archive.apache.org/dist/maven/maven-3/3.2.5/binaries/)

이클립스 `Window` - `Preferences` - `Maven` - `Installations` 메뉴를 보면 이클립스가 사용중인 메이븐 버전이 표시됨. 보통 EMBEDDED(m2e 확장기능에 포함된 것)를 사용중일 텐데, `Add` 버튼을 눌러서 다운로드 받은 3.2.5 버전의 경로를 지정한 후 해당 버전을 사용한다고 설정하면 된다.
