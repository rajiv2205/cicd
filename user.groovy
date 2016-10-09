import hudson.model.*
import hudson.security.*
import hudson.tasks.Mailer

def userId = args[0]
def password = args[1]
def email = args[2]
def instance = Jenkins.instance
def existingUser = instance.securityRealm.allUsers.find {it.id == userId}

if (existingUser == null) {
    def user = instance.securityRealm.createAccount(userId, password)
    user.addProperty(new Mailer.UserProperty(email));

    def strategy = new GlobalMatrixAuthorizationStrategy()
    strategy.add( Hudson.READ, userId)
    instance.setAuthorizationStrategy(strategy)
    instance.save()
} 
