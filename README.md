# MVPStarter

A repository to quick start an Android project in MVP architecture.

## Usage

1. Clone MVPStarter to appropriate project folder

    Example: `git clone http://farhan.ali@192.168.3.90:7990/scm/aar/mvpstarter.git <project_folder_name>`

2. Update *package* entry in *AndroidManifest.xml* to appropriate one

    Example: `package="com.tecsol.mvpstarter"` to `package="com.mtvindia.connect"`

3. Update *applicationId* in app *build.gradle*

    Example: `applicationId "com.tecsol.mvpstarter"` to `applicationId "com.mtvindia.connect"`

4. Rename *MvpApplication* class within package *<package_name>.app* to appropriate one

    Example: `com.mtvindia.connect.app.MvpApplication` to `com.mtvinida.connect.app.MtvConnectApplication`

5. Update *USER_AGENT* config in *.app.Config* to appropriate one

    Example: `USER_AGENT = "Tecsol-MVPStarter-App"` to `USER_AGENT = "MtvConnect-Android-App"`

6. Update *strings.xml* with appropriate values

7. Remove sample activity, presenter and layout files

8. Update/remove this README.md

9. Remove current git information and init with new project git setup, for example:

        cd <project_folder_name>

        # remove existing .git folder if any
        rm -rf .git
        
        # init new git & add remote
        git init
        git remote add origin http://some_git_server/some_project.git

        # initial commit to master only with readme file
        git add README.md
        git commit -m "Initial commit"
        git push -u origin master

        # create develop branch and checkout
        git checkout -b develop

        # initial commit to develop with all other files
        git add --all
        git commit -m "Initial Commit"
        git push -u origin develop


