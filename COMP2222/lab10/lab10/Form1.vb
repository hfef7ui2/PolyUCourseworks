Public Class Form1
  Dim choice As Integer = 0
  Private Sub Timer1_Tick(sender As Object, e As EventArgs) Handles Timer1.Tick
    Static x As Integer = 0
    If choice = 0 Then
      If x Mod 2 = 0 Then
        Me.PictureBox1.Image = My.Resources.bat_1
      Else
        Me.PictureBox1.Image = My.Resources.bat_2
      End If
    Else
      If x Mod 2 = 0 Then
        Me.PictureBox1.Image = My.Resources.bat_3
      Else
        Me.PictureBox1.Image = My.Resources.bat_4
      End If
    End If

    x += 1
  End Sub

  Private Sub PictureBox1_Click(sender As Object, e As EventArgs) Handles PictureBox1.Click
    Static y As Integer = 0
    If y Mod 2 = 0 Then
      Timer1.Stop()
    Else
      Timer1.Start()
    End If
    y += 1
  End Sub

  Private Sub Form1_KeyDown(ByVal sender As System.Object, ByVal e As System.Windows.Forms.KeyEventArgs) Handles MyBase.KeyDown
    ' Sets Handled to true to prevent other controls from 
    ' receiving the key if an arrow key was pressed
    Dim bHandled As Boolean = False
    Static z As Integer = 0
    Select Case e.KeyCode
      Case Keys.Right
        choice = 0
        Me.PictureBox1.Left = Me.PictureBox1.Left + 40
        e.Handled = True
      Case Keys.Left
        choice = 1
        Me.PictureBox1.Left = Me.PictureBox1.Left - 40
        e.Handled = True
      Case Keys.Up
        Me.PictureBox1.Top = Me.PictureBox1.Top - 40
        e.Handled = True
      Case Keys.Down
        Me.PictureBox1.Top = Me.PictureBox1.Top + 40
        e.Handled = True
    End Select
  End Sub

End Class
