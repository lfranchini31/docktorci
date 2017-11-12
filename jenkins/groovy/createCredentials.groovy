import jenkins.model.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.*

// JENKINS SLAVE ACCOUNT
def jenkinsSlaveCredentialsClass = [
        'id':'jenkinsSlaveCredentialsId',
        'username':'jenkins',
        'sshKeyPath':'/run/secrets/jenkinsSlaveKeys/id_rsa',
        'passphrase':'',
        'description':'Jenkins Slave Credentials']
def jenkinsSlaveCredentials = new BasicSSHUserPrivateKey(
        CredentialsScope.GLOBAL,
        jenkinsSlaveCredentialsClass.id,
        jenkinsSlaveCredentialsClass.username,
        new BasicSSHUserPrivateKey.FileOnMasterPrivateKeySource(
                jenkinsSlaveCredentialsClass.sshKeyPath),
        jenkinsSlaveCredentialsClass.passphrase,
        jenkinsSlaveCredentialsClass.description)

// ARTIFACTORY ACCOUNT
def artifactoryCredentialsRaw = new File("/run/secrets/artifactory").text.trim().split(":")
def artifactoryCredentialsClass = [
        'id':'artifactoryCredentialsId',
        'username':artifactoryCredentialsRaw[0],
        'password':artifactoryCredentialsRaw[1],
        'description':'Artifactory Credentials']
def artifactoryCredentials = new UsernamePasswordCredentialsImpl(
        CredentialsScope.GLOBAL,
        artifactoryCredentialsClass.id,
        artifactoryCredentialsClass.description,
        artifactoryCredentialsClass.username,
        artifactoryCredentialsClass.password)

// GITLAB ACCOUNT
def gitlabCredentialsClass = [
        'id':'gitlabCredentialsId',
        'username':'jenkins',
        'sshKeyPath':'/run/secrets/gitlabKeys/id_rsa',
        'passphrase':'',
        'description':'GitLab Credentials']
def gitlabCredentials = new BasicSSHUserPrivateKey(
        CredentialsScope.GLOBAL,
        gitlabCredentialsClass.id,
        gitlabCredentialsClass.username,
        new BasicSSHUserPrivateKey.FileOnMasterPrivateKeySource(
                gitlabCredentialsClass.sshKeyPath),
        gitlabCredentialsClass.passphrase,
        gitlabCredentialsClass.description)

def global_domain = Domain.global()
def credentials_store = Jenkins.instance
        .getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0]
        .getStore()
credentials_store.addCredentials(global_domain, jenkinsSlaveCredentials)
credentials_store.addCredentials(global_domain, artifactoryCredentials)
credentials_store.addCredentials(global_domain, gitlabCredentials)
