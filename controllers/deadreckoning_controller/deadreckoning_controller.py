"""deadreckoning_controller controller."""

from controller import Supervisor
import pioneer_nav1 as pn
import math
from pioneer_nav1 import MoveState

def run_robot(robot):
    
    odometry_display = robot.getDevice('odometryDisplay')
    
    # get the time step of the current world.
    timestep = int(robot.getBasicTimeStep())

    nav = pn.PioneerNavigation(robot)
    time_elapsed = 0
    target_time = 0
    robot_velocity = 0.3
    
    # define schedule
    # schedule = [ MoveState.CONFIGURE, MoveState.STOP ]
    schedule = [ MoveState.FORWARD, MoveState.STOP ]
    schedule_index = -1 # we increment before selecting the current action
        
    display_action = ""

    if (schedule[0] == MoveState.CONFIGURE):
        schedule_index = schedule_index + 1
        nav.configure_initialise_parameters(2*math.pi)
        
    while robot.step(timestep) != -1:
        if (nav.state == MoveState.CONFIGURE):
            # Special case for checking robot parameters
            display_action = nav.configure_check_parameters(timestep)
        elif (time_elapsed > target_time):
            time_elapsed = 0
            # select next action in schedule if not stopped
            schedule_index = (schedule_index + 1) % len(schedule)
            nav.state = schedule[schedule_index]
            if (nav.state == MoveState.FORWARD):
                target_time = nav.forward(0.5, robot_velocity)
                display_action = "Forward Action: 0.5m"
                
            elif (nav.state == MoveState.CONFIGURE):
                display_action = "Determine Wheel / Axel Parameters" 
                          
            elif (nav.state == MoveState.STOP):
                nav.stop()
                display_action = "Stop for 1 minute"
                target_time = 60 * 1000
            elif (nav.state == MoveState.ARC):
                target_time = nav.arc(math.pi/2.0, 0.7, robot_velocity)
                display_action = "Arc Action around an ICR 0.7 away"
                
        else:
            time_elapsed += timestep
            
            
            
            
            
        # odo display
        if odometry_display is not None:
            odometry_display.setColor(0xFFFFFF)
            # white
            odometry_display.fillRectangle(0,0, odometry_display.getWidth(), odometry_display.getHeight())
            odometry_display.setColor(0x000000)
            # black
            odometry_display.setFont("Arial", 18, True)
            odometry_display.drawText("Robot State", 1, 1)
            odometry_display.setFont("Arial", 12, True)
            if (display_action != ""):
                odometry_display.drawText(display_action, 1, 30)
            true_pose = nav.get_real_pose()
            odometry_display.drawText(f'True pose:{true_pose}', 1, 50)
            
        # true_pose = nav.get_real_pose()
        # print(f"Action: {display_action} \tTrue Pose: {true_pose}")

   
if __name__ == "__main__":
    # create the Supervised Robot instance.
    my_robot = Supervisor()
    run_robot(my_robot)

# Aims
# Create a state machine that takes a schedule of commands and runs indefinitely
# Display current move and position on display