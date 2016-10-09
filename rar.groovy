job("WS Release") {
  logRotator(60, 20, 1, -1)
  description('I\'ll update launch config for new release')
  properties {
    parameters {
      parameterDefinitions {
        stringParam {
          name('WS_AMI_ID')
          defaultValue(' ')
          description('Put your AMI ID to update the launch config for autoscaling')
        }
      }
    }
  }
  scm {
    git {
      branch('*/master')
      remote {
        name('')
        refspec('')
        url('git@github.com:softdive/healtharx.git')
      }
    }     
  }
  environmentVariables {
    propertiesFile('/var/lib/jenkins/.aws.properties')
    keepBuildVariables(true)
  }

  steps {
    shell('#/bin/bash\necho "####################################### AMI ID${WS_AMI_ID} ####################################"\ncd beato-devops/terraform\n/usr/local/bin/terraform apply -target=aws_autoscaling_group.app_ws_asg -var "aws_scaling_ws_ami.ap-southeast-1=${WS_AMI_ID}" -var "public_key_path=terraform.pub" -var "key_name=beatoapp-prod"\n/usr/local/bin/terraform apply -target=aws_autoscaling_group.app_ws_asg -var "aws_scaling_ws_ami.ap-southeast-1=${WS_AMI_ID}" -var "public_key_path=terraform.pub" -var "key_name=beatoapp-prod" -var "desired-capacity=4"\nsleep 3m\n/usr/local/bin/terraform apply -target=aws_autoscaling_group.app_ws_asg -var "aws_scaling_ws_ami.ap-southeast-1=${WS_AMI_ID}" -var "public_key_path=terraform.pub" -var "key_name=beatoapp-prod" -var "desired-capacity=2"\ngit config --global user.name "zenkinbeatoapp"\ngit config --global user.email "abhishek@beatoapp.com"\ngit add --all\ngit commit -m "jenkins-${JOB_NAME}-${BUILD_NUMBER}"')
    }
publishers {
    gitPublisher {
        pushOnlyIfSuccess(true)
        pushMerge(true)
        forcePush(true)          
        branchesToPush {
                branchToPush {
                        targetRepoName('origin')
                        branchName('master')
                }
        }
    }
}
}

